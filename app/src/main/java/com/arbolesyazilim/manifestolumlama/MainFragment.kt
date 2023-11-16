package com.arbolesyazilim.manifestolumlama

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arbolesyazilim.manifestolumlama.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val manifestList = Constants.getManifests()
    private var currentIndex: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // İlk manifesti göster
        showNextManifest()

        // Sayfayı yukarı kaydırdığında yeni bir rastgele manifest göster
        binding.swipeRefreshLayout.setOnRefreshListener {
            showNextManifest()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        // Sayfayı aşağı kaydırdığında bir önceki manifesti göster
        binding.textView.setOnClickListener {
            showPreviousManifest()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun showNextManifest() {
        // Rastgele bir kelime seç
        val randomIndex = generateRandomIndex()
        currentIndex = randomIndex
        val secilenManifest = manifestList[currentIndex].word

        // TextView'e seçilen kelimeyi set et
        binding.textView.text = secilenManifest

    }

    private fun showPreviousManifest() {
        // Bir önceki manifesti göster
        if (currentIndex > 0) {
            currentIndex--
            val secilenManifest = manifestList[currentIndex].word
            binding.textView.text = secilenManifest

        }
    }

    private fun generateRandomIndex(): Int {
        // Rastgele bir in"deks oluştur
        return (0 until manifestList.size).random()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
